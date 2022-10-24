<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var = "cp" value = "<%=request.getContextPath()%>"/>
<!DOCTYPE html>
<html>
<head>

    <title>회원정보 수정</title>

</head>


<body>

<div id="posts_list">
    <div class="container col-md-4">
        <form>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <label for="id"></label>
            <input type="hidden" id="id" value="${memberDTO.id}"/>
            <div class="form-group">
                <label for="userId">아이디</label>
                <input type="text" id="userId" value="${memberDTO.userId}" class="form-control" readonly/>
            </div>

            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" name="pwd" id="pwd" class="form-control" placeholder="수정할 비밀번호를 입력해주세요"/>
                <span id="valid_pwd">${valid_pwd}</span>
            </div>

            <div class="form-group">
                <label>비밀번호 재확인</label>
                <input type="password" name="pwdCheck" id="pwdCheck" class="form-control" placeholder="비밀번호를 입력해주세요"/>
            </div>

            <div class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</div>
            <div class="alert alert-danger" id="alert-danger">비밀번호가 일치하지 않습니다.</div>

            <div class="form-group">
                <label for="name">이름</label>
                <input type="text" id="name" value="${memberDTO.name}" class="form-control" readonly/>
            </div>

            <div class="form-group">
                <label for="nickname">닉네임</label>
                <input type="text" id="nickname" value="${memberDTO.nickname}" class="form-control" placeholder="수정할 닉네임을 입력해 주세요."/>
            </div>

            <div class="form-group">
                <label>생년월일</label>
                <input type="date" name="birth" value="${memberDTO.birth}" class="form-control" readonly/>
            </div>

            <div class="form-group">
                <label for="tel">휴대전화 번호</label>
                <input type="text" id="tel" value="${memberDTO.tel}" class="form-control" placeholder="수정할 휴대전화 번호를 입력해 주세요."/>
            </div>

            <div class="form-group">
                <label>우편번호</label> &nbsp; <input type="button" value="우편번호 찾기" id="postcodeBtn" onclick="sample6_execDaumPostcode()">
                <input type="text" id="postcode" value="${memberDTO.postcode}" class="form-control" placeholder="수정할 우편번호를 입력해 주세요." readonly="readonly"/>
            </div>

            <div class="form-group">
                <label for="address">주소</label>
                <input type="text" id="address" value="${memberDTO.address}" class="form-control" placeholder="수정할 주소를 입력해 주세요." readonly="readonly"/>
            </div>

            <div class="form-group">
                <label for="detailAddress">상세주소</label>
                <input type="text" id="detailAddress" value="${memberDTO.detailAddress}" class="form-control" placeholder="수정할 상세주소를 입력해 주세요."/>
            </div>

            <div class="form-group">
                <label for="extraAddress">참고사항</label>
                <input type="text" id="extraAddress" value="${memberDTO.extraAddress}" class="form-control" placeholder="참고사항을 입력해 주세요."/>
            </div>

            <div class="form-group">
                <label for="email">이메일</label>
                <input type="email" id="email" value="${memberDTO.email}" class="form-control" placeholder="수정할 이메일을 입력해 주세요."/>
            </div>

            <div class="form-group">
                <label for="memberImageName">프로필 사진</label>
                <input type="text" id="memberImageName" value="${memberDTO.memberImageName}" class="form-control" placeholder="수정할 프로필 사진을 설정해 주세요."/>
            </div>

        </form>
        <button id="btn-member-modify" class="btn btn-primary bi bi-check-lg" oninput="memberModify()">완료</button>
        <a href="/member/mypage" role="button" class="btn btn-info bi bi-arrow-return-left"> 마이 페이지</a>
    </div>
</div>

</body>
</html>