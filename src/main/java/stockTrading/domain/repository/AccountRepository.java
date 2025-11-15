package stockTrading.domain.repository;

import stockTrading.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {

    // 저장
    public void add(Account account);

    // 조회
    Optional<Account> fingById(String id);

    // 수정



    // 삭제
}
