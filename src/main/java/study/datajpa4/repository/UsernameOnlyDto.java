package study.datajpa4.repository;

public class UsernameOnlyDto {

    private final String username;

    //이 생성자의 파라미터명(username)을 분석해서 프로젝션을 사용한다. 그래서 파라미터명이 바뀌면 안됨
    public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
