package ru.netology.mycloudstorage.repositopy;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.mycloudstorage.modele.MyToken;

public interface TokenRepositiry extends JpaRepository<MyToken, Integer> {
    @Override
    <S extends MyToken> S save(S entity);
}
