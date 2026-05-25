package backend.mapper;

import backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ユーザー Mapper
 */
@Mapper
public interface UserMapper {

    /**
     * メールアドレスからユーザー取得
     *
     * @param email メールアドレス
     * @return ユーザー情報
     */
    User findByEmail(
            @Param("email") String email
    );

    /**
     * メールアドレス重複チェック
     *
     * @param email メールアドレス
     * @return 件数
     */
    int countByEmail(
            @Param("email") String email
    );

    /**
     * ユーザー登録
     *
     * @param user ユーザー情報
     */
    void insertUser(User user);
}