package subway.member.domain;

import subway.exception.LoginFailException;
import subway.exception.TooLowAgeException;
import subway.exception.WrongEmailFormatException;

import java.util.Objects;

public class Member {
    private Long id;
    private String email;
    private String password;
    private Integer age;

    public Member() {
    }

    public Member(String email, String password) {
        String emailRule = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!email.matches(emailRule)) {
            throw new WrongEmailFormatException();
        }
        this.email = email;
        this.password = password;
    }

    public Member(String email, String password, Integer age) {
        this(email, password);
        if (age < 1) {
            throw new TooLowAgeException();
        }
        this.age = age;
    }

    public Member(Long id, String email, String password, Integer age) {
        this(email, password, age);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }

    public void checkValidMember(Member member) {
        if (!this.equals(member)) {
            throw new LoginFailException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(email, member.email) && Objects.equals(password, member.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
