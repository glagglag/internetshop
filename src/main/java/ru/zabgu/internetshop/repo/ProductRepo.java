package ru.zabgu.internetshop.repo;

import org.aspectj.bridge.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Message, Long> {
}
