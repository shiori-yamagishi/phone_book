package com.ojtproject.phonebook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojtproject.phonebook.entity.PhoneBook;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook, Long> {

	//**検索SQL*/
	@Query(value = "SELECT p.id, p.name, p.phone_number FROM phone_book p", nativeQuery = true)
	public List<PhoneBook> findAll();

	//**条件一致検索SQL*/
	@Query(value = "SELECT p.id, p.name, p.phone_number FROM phone_book p where p.name like %:keyword%", nativeQuery = true)
	public List<PhoneBook> findResult(@Param("keyword") String keyword);

	//**削除SQL*/
	@Modifying
	@Transactional
	@Query(value = "DELETE from phone_book WHERE id = :id", nativeQuery = true)
	public void delete(@Param("id") int id);

	//**登録SQL*/
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO phone_book (name,phone_number,id) VALUES (:name,:phoneNumber,:id)", nativeQuery = true)
	public void regist(@Param("name") String name, @Param("phoneNumber") String phoneNumber, @Param("id") int id);

	//**更新SQL*/
	@Modifying
	@Transactional
	@Query(value = "UPDATE phone_book SET name = :name, phone_number = :phoneNumber ,id = :Id WHERE id = :id", nativeQuery = true)
	public void update(@Param("name") String name, @Param("phoneNumber") String phoneNumber, @Param("id") int id);
}