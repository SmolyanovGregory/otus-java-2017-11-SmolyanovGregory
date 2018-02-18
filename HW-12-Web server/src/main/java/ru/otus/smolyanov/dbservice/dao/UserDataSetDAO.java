package ru.otus.smolyanov.dbservice.dao;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 12 - Web server
 */

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import ru.otus.smolyanov.base.UserDataSet;

public class UserDataSetDAO {

  private final Session session;

  public UserDataSetDAO(Session session) {
    this.session = session;
  }

  public void save(UserDataSet user){
    Transaction tr = session.beginTransaction();
    session.saveOrUpdate(user);
    tr.commit();
  }

  public UserDataSet load(long id){
    return session.load(UserDataSet.class, id);
  }

  public List<UserDataSet> loadAll(){
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
    criteria.from(UserDataSet.class);

    return session.createQuery(criteria).list();
  }
}
