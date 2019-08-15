package ru.bis.demolibraryproject.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorSpecificationsBuilder<T> {
    private final List<SearchCriteria> params;

    public AuthorSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public AuthorSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty()) {
            return null;
        }
        List<Specification> specs = params.stream()
                .map(AuthorSpecification::new)
                .collect(Collectors.toList());
        Specification result = specs.get(0);
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
