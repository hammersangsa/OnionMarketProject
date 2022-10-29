<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var = "cp" value = "<%=request.getContextPath()%>"/>
<style>
    .checkout__input input {
        width: 100%;
        height: 46px;
        border: 1px solid #b7b7b7;
        padding-left: 20px;
        font-size: 16px;
        color: #000000;
        border-radius: 4px;
    }

    .alert-danger {
        margin-top: 1px;
        background-color: #ffffff;
        color: red;
        border: none;
    }

</style>

<section class="spad">
    <div class="container">
        <div class="checkout__form">
            <form action="/member/loginProc" method="post" name="myForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="row">
                    <div class="col-lg-3 col-md-3"></div>
                    <div class="col-lg-6 col-md-6">
                        <h4 style="text-align: center">로그인</h4>
                        <div class="checkout__input">
                            <p>아이디</p>
                            <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디를 입력해주세요">
                        </div>

                        <div class="checkout__input">
                            <p>비밀번호</p>
                            <input type="password" name="pwd" id="pwd" placeholder="비밀번호를 입력해 주세요."/>
                        </div>

                <c:if test="${not empty error}">
                    <p id="valid" class="alert alert-danger">${exception}</p>
                </c:if>

                <button class="site-btn">로그인</button>
                <a href="/member/join" role="button" class="site-btn">회원가입</a>
                <a href="/member/findId" role="button" class="site-btn">아이디 찾기</a>
                <a href="/member/findPwd" role="button" class="site-btn">비밀번호 찾기</a>
            </form>
        </div>
    </div>
</section>