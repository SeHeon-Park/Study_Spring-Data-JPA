package study.datajpa.datajpa.repository;

// projection
public interface UserNameOnly {  // class로도 가능(class로 projection하면 생성자에서 이름 맞춰야함)
                                 // dto로 변환하기 귀찮을때 쓰면 되겟군

    String getUserName(); // 첫번째 있는애는 최적화됨(root entity)
    TeamInfo getTeam();   // 두번재 부터 최적화 안됨(Entity조회해서 다시 계산)

    interface TeamInfo{
        String getName();
    }
}
