package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dto.profiles;
import dto.user;


public class dao {
EntityManagerFactory factory = Persistence.createEntityManagerFactory("m7");
EntityManager manager = factory.createEntityManager();
EntityTransaction transaction=manager.getTransaction();

public void saveUser(user u) {
	transaction.begin();//execution start
	manager.persist(u);//store
	transaction.commit();//save
}
//it will search for email and mobile from your database  ?=email or mobile which is going to fetch
public List<user> finduserbyemail(String email)
{
	return manager.createQuery("select x from user x where email=?1").setParameter(1, email).getResultList();
	
}
public List<user> finduserbymobile(long mobile)
{
	return manager.createQuery("select x from user x where mobile=?1").setParameter(1, mobile).getResultList();
	
}

public List<profiles> fetchTasksByUserId(int userId){
	return manager.createQuery("select x from Task x where user_id=?1").setParameter(1, userId).getResultList();
}
public void saveProfile(profiles prof) {
	transaction.begin();
	manager.persist(prof);
	transaction.commit();
}
}
