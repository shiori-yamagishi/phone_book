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
	@Query(value = "SELECT p.id, p.name, p.phone_number, p.address FROM phone_book p ORDER BY p.id", nativeQuery = true)
	public List<PhoneBook> findAll();

	//**条件一致検索SQL*/
	@Query(value = "SELECT p.id, p.name, p.phone_number, p.address FROM phone_book p WHERE p.name LIKE %:keyword% ORDER BY p.id", nativeQuery = true)
	public List<PhoneBook> findByKeyword(@Param("keyword") String keyword);

	@Query(value = "SELECT p.id, p.name, p.phone_number, p.address FROM phone_book p WHERE p.address = :address ORDER BY p.id", nativeQuery = true)
	public List<PhoneBook> findByAddress(@Param("address") String address);

	@Query(value = "SELECT p.id, p.name, p.phone_number, p.address FROM phone_book p WHERE p.name LIKE %:keyword% AND p.address = :address ORDER BY p.id", nativeQuery = true)
	public List<PhoneBook> findByKeywordAndAdress(@Param("keyword") String keyword, @Param("address") String address);

	//**削除SQL*/
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM phone_book WHERE id = :id", nativeQuery = true)
	public void delete(@Param("id") int id);

	//**登録SQL*/
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO phone_book (name,phone_number,address) VALUES (:name,:phoneNumber,:address)", nativeQuery = true)
	public void regist(@Param("name") String name, @Param("phoneNumber") String phoneNumber, @Param("address") String address);

	//**更新SQL*/
	@Modifying
	@Transactional
	@Query(value = "UPDATE phone_book SET name = :name, phone_number = :phoneNumber, address = :address, id = :id WHERE id = :id", nativeQuery = true)
	public void update(@Param("name") String name, @Param("phoneNumber") String phoneNumber, @Param("id") int id, @Param("address") String address);

}