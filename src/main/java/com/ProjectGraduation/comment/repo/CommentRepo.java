package com.ProjectGraduation.comment.repo;

import com.ProjectGraduation.comment.entity.Comment;
import com.ProjectGraduation.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByProduct(Product product);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
