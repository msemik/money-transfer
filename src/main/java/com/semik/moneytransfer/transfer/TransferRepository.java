package com.semik.moneytransfer.transfer;

import com.semik.moneytransfer.transfer.model.Transfer;
import com.semik.moneytransfer.transfer.model.TransferTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;

@Repository
public interface TransferRepository extends ReactiveMongoRepository<Transfer, String> {

    default Flux<TransferTO> findAllTO() {
        return findAll()
                .map(TransferTO::new);
    }
}
