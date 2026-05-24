package backend.repository;

import backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // メールアドレスからユーザーを取得（ログイン認証で使用）
    Optional<User> findByEmail(String email);

    // メールアドレスの重複チェック用（ユーザー登録で使用）
    boolean existsByEmail(String email);
}