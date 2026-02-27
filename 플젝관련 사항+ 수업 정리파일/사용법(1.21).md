# env파일로 비밀번호등의 개인 정보 관리할것인가 아니면 운영체제에 직접 넣을 것이냐 선택하라.
# env파일로 자기것만 넣어 사용해보자.
# 커서를 작성해야 sql문 사용 가능

# (지금은 셀렉트만 하는 부분 나머지는 이렇게까지 하지 않는다.) 여기까지가 커서 커넥트후 제거까지 흐름 접속한다 => 커서 함수 쓴다=> 뽑아온다=> 작성 정리한다=> 커서 닫는다=> 커넥션 닫는다.

    # 전체의 행일 때
        # result = [dict(zip(columns, row)) for row in rows]

        # 하나의 행일 때
        # result = dict(zip(columns, row)) if row else None
        # fetchone() : 하나의 행을 받을때 쓰는 함수
        # fetchall() : 전체의 행을 받을때 쓰는 함수
# sql에서 delete 행 하나를 지울때 쓰는 용어  auto-incretment 는 제거 불가
# drop은 ddl용어로 행에 auto_incretment가 있는것도 초기화
# truncate ddl용어로 drop과 create가 있고 이를 한 번에 합친게 truncate가 있다.