package study.datajpa4.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    @Value("#{target.username + ' ' + target.age}") //오픈 프로젝션: 일단 DB에서 target(여기에선 Member)에 대한걸 다 조회해오고 어플리케이션에서 target값을 뽑아내는 방법.
    //클로우즈 프로젝션: 원하는 컬럼만 딱 가져오는 조회 방법
    String getUsername();
}
