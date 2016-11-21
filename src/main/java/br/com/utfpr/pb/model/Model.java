package br.com.utfpr.pb.model;

import java.io.Serializable;

/**
 * Created by João on 10/11/2016.
 */
public interface Model<ID extends Serializable> extends Serializable {

    ID getId();

}
