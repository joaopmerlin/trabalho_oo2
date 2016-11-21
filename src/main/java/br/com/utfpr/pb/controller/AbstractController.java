package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.model.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by João on 10/11/2016.
 */
public abstract class AbstractController<T extends Model, ID extends Serializable> {

    protected abstract AbstractDao<T, ID> getDao();

    public T save(T entidade) {
        return getDao().save(entidade);
    }

    public T find(ID id) {
        return getDao().find(id);
    }

    public List<T> findAll() {
        return getDao().findAll();
    }

    public void remove(T entidade) {
        getDao().remove(entidade);
    }

}
