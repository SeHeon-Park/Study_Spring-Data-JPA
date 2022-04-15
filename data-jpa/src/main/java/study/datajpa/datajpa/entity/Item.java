package study.datajpa.datajpa.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Item implements Persistable<String> {
    @Id
    private String id; // GeneratedValue를 쓰지않고 직접 id를 넣어주는 경우 implements Persistable<String>
                       // 안해주면 save함수의 merge로 넘어가버림
                       // GeneratedValue는 persist해야 id값을 가짐
    private LocalDateTime createdAt;

    public Item(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return createdAt == null;  // createdAt이 null이면 새로운 객체이다!
    }
}
