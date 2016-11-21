package br.com.utfpr.pb.dao;

import br.com.utfpr.pb.model.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by João on 10/11/2016.
 */
public interface AbstractDao<T extends Model, ID extends Serializable> {

    T save(T entidade);

    T find(ID id);

    List<T> findAll();

    void remove(T entidade);
}
