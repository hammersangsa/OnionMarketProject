package com.youprice.onion.util;


import com.youprice.onion.entity.member.Member;
import org.apache.commons.mail.HtmlEmail;

public class MailUtil {

    public void sendMail(Member member) throws Exception{

        // Mail Server 설정
        String charSet = "utf-8";
        String host = "smtp.gmail.com";
        String userId = "youpriceonion";
        String password = "lpnfqfiidkokreis";

        // 보내는 사람 정보
        String fromEmail = "youpriceonion@gmail.com";
        String fromName = "ONION";

        // 메일 정보
        String subject = "";
        String content = "";

        subject = "[ONION MARKET] 임시 비밀번호 발급 안내";
        content += "<div align='left'>";
        content += "<h3>";
        content += member.getUserId() + "님의 임시 비밀번호입니다.<br>";
        content += "로그인 후 반드시 비밀번호를 변경해 주세요!";
        content += "<p>임시 비밀번호 : ";
        content += member.getPwd() + "</p></div>";

        // 받는 사람 정보
        String toEmail = member.getEmail();

        // 이메일 전송
        try {
            HtmlEmail mail = new HtmlEmail();
            mail.setDebug(true);
            mail.setCharset(charSet);
            mail.setSSLOnConnect(true); // TLS가 없는 경우 SSL 사용
            mail.setHostName(host);
            mail.setSmtpPort(587); // SMTP port 번호
            mail.setAuthentication(userId, password);
            mail.setStartTLSEnabled(true); // TLS 사용
            mail.addTo(toEmail, charSet);
            mail.setFrom(fromEmail, fromName, charSet);
            mail.setSubject(subject);
            mail.setHtmlMsg(content);
            mail.send();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
