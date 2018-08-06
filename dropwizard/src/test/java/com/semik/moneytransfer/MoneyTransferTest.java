package com.semik.moneytransfer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.assertj.core.api.AbstractIntegerAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class  MoneyTransferTest {
    private static final String TMP_FILE = createTempFile();
    private static final String CONFIG_PATH = new File("./config.yml").getAbsolutePath();

    @Rule
    public final DropwizardAppRule<MoneyTransferConfiguration> RULE =
            new DropwizardAppRule<MoneyTransferConfiguration>(
                    MoneyTransferApplication.class,
                    CONFIG_PATH,
                    ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE));
    private Client client;

    @Before
    public void setUp() {
        client = RULE.client();
    }

    @Test
    public void successfulTransfer() {
        Response transferResponse = client
                .target("http://localhost:" + RULE.getLocalPort() + "/money-transfer")
                .request()
                .post(Entity.json(createTransfer(3)));

        assertThat(transferResponse.getStatus()).isEqualTo(204);

        Response allTransfers = client
                .target("http://localhost:" + RULE.getLocalPort() + "/money-transfer")
                .request()
                .get();

        assertStoredTransferIsCorrect(allTransfers);
        assertBalanceOfAccountWithId(1).isEqualTo(3);
        assertBalanceOfAccountWithId(2).isEqualTo(7);
    }

    @Test
    public void notEnoughMeansForTransfer() {
        Response transferResponse = client
                .target("http://localhost:" + RULE.getLocalPort() + "/money-transfer")
                .request()
                .post(Entity.json(createTransfer(11)));

        assertThat(transferResponse.getStatus()).isEqualTo(412);
        assertThat(transferResponse.readEntity(ObjectNode.class).get("message").asText())
                .isEqualTo("No sufficient means to charge balance with 10 cents for 11");

    }

    private void assertStoredTransferIsCorrect(Response getAllResponse) {
        assertThat(getAllResponse.getStatus()).isEqualTo(200);
        JsonNode entity = getAllResponse.readEntity(JsonNode.class);
        assertThat(entity.getNodeType()).isEqualTo(JsonNodeType.ARRAY);
        ArrayNode array = (ArrayNode) entity;
        JsonNode transfer = array.get(0);
        assertThat(transfer.get("id")).isNotNull();
        assertThat(transfer.get("cents").asInt()).isEqualTo(3);
        JsonNode sourceAccount = transfer.get("source");
        assertThat(sourceAccount.get("accountId").asInt()).isEqualTo(2);
        assertThat(sourceAccount.get("balanceInCents").asInt()).isEqualTo(10);
        JsonNode destinationAccount = transfer.get("destination");
        assertThat(destinationAccount.get("accountId").asInt()).isEqualTo(1);
        assertThat(destinationAccount.get("balanceInCents").asInt()).isEqualTo(0);
    }

    private AbstractIntegerAssert<?> assertBalanceOfAccountWithId(int id) {
        Response getAllResponse = client
                .target("http://localhost:" + RULE.getLocalPort() + "/account/" + id)
                .request()
                .get();
        ObjectNode account = getAllResponse.readEntity(ObjectNode.class);
        int balanceInCents = account.get("balanceInCents").asInt();
        return assertThat(balanceInCents);
    }

    private String createTransfer(int cents) {
        return JsonNodeFactory.instance.objectNode()
                .put("sourceAccountId", 2)
                .put("destinationAccountId", 1)
                .put("cents", cents)
                .toString();
    }

    private static String createTempFile() {
        try {
            return File.createTempFile("test-example", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}