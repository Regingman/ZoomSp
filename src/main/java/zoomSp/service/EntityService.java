package zoomSp.service;


import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * @author Atai
 * @param <T>
 * @param <S>
 */
@Service
public interface EntityService<T,S> {

    List<T> getAll();

    T getById(S id);

    T save(T t);

    void delete(S id);
}