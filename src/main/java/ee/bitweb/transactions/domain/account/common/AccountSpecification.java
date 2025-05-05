package ee.bitweb.transactions.domain.account.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountSpecification {

    public static Specification<Account> byNumberIn(List<String> numbers) {
        return (root, query, builder) -> root.get(Account_.number).in(numbers);
    }
}
