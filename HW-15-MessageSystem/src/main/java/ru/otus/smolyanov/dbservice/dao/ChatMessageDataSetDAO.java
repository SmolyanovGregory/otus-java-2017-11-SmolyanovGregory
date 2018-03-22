package ru.otus.smolyanov.dbservice.dao;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 15 (message system)
 */

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import ru.otus.smolyanov.base.ChatMessageDataSet;

public class ChatMessageDataSetDAO {

  private final Session session;

  public ChatMessageDataSetDAO(Session session) {
    this.session = session;
  }

  public void save(ChatMessageDataSet chatMessage){
    Transaction tr = session.beginTransaction();
    session.saveOrUpdate(chatMessage);
    tr.commit();
  }

  public ChatMessageDataSet load(long id){
    return session.load(ChatMessageDataSet.class, id);
  }

  public List<ChatMessageDataSet> loadAll(){
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<ChatMessageDataSet> criteria = builder.createQuery(ChatMessageDataSet.class);
    criteria.from(ChatMessageDataSet.class);

    return session.createQuery(criteria).list();
  }
}
