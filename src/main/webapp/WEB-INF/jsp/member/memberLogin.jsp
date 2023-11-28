<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../inc/header.jsp" %>
    <script>
        function fn_formSubmit() {
            if (!chkInputValue("#userid", "<s:message code="common.id"/>")) return false;
            if (!chkInputValue("#userpw", "<s:message code="common.password"/>")) return false;

            $("#form1").submit();
        }
    </script>

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign In</h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="/login-process" method="post" id="form1" name="form1">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="ID" name="userid" id="userid" type="email" autofocus value="<c:out value="${userid}"/>">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="userpw" id="userpw" type="password" value="" onkeydown="if(event.keyCode == 13) { fn_formSubmit();}">
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input name="remember" type="checkbox" value="Y" <c:if test='${userid != null && userid != ""}'>checked</c:if>>Remember Me
                                </label>
                            </div>
                            <!-- Change this to a button or input when using this as a form -->
                            <a href="#" class="btn btn-lg btn-success btn-block" onclick="fn_formSubmit()">Login</a>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<%@include file="../inc/footer.jsp" %>