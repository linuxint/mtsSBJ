<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><s:message code="common.pageTitle"/></title>
    <link href="css/sb-admin/bootstrap.min.css" rel="stylesheet">
    <link href="css/sb-admin/metisMenu.min.css" rel="stylesheet">
    <link href="css/sb-admin/sb-admin-2.css" rel="stylesheet">
    <link href="css/sb-admin/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href='js/fullcalendar5/main.css' rel='stylesheet' />
    <style>

        #calendar-container {
            position: fixed;
            top: 250px;
            left: 270px;
            right: 20px;
            bottom: 20px;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="js/jquery-2.2.3.min.js"></script>
    <script src="css/sb-admin/bootstrap.min.js"></script>
    <script src="css/sb-admin/metisMenu.min.js"></script>
    <script src="css/sb-admin/sb-admin-2.js"></script>
    <script src='js/fullcalendar/moment.min.js'></script>
    <script src='js/fullcalendar5/main.js'></script>
    <script src='js/fullcalendar5/locales-all.js'></script>

    <script src="js/mts.js"></script>
    
<script>

    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');
        var data = [
            <c:forEach var="listview" items="${listview}" varStatus="status">
            <c:if test="${listview.tsstartdate != ''}">
            {"id":'<c:out value="${listview.tsno}" />'
                ,"title":'<c:out value="${listview.tstitle}" />'
                ,"start":"<c:out value="${listview.tsstartdate}" />"
                <c:if test="${listview.tsenddate != ''}">
                ,"end":"<c:out value="${listview.tsenddate}" />"
                </c:if>
                ,"color": "<c:out value="${listview.statuscolor}" />"} <c:if test="${!status.last}">,</c:if>
            </c:if>
            </c:forEach>
        ];
        var calendar = new FullCalendar.Calendar(calendarEl, {
            height: '100%',
            expandRows: true,
            slotMinTime: '08:00',
            slotMaxTime: '20:00',
            headerToolbar: {
                left: 'prevYear,prev,next,nextYear today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
            },
            initialView: 'dayGridMonth',
            initialDate: new Date(),
            locale: 'ko',
            navLinks: true, // can click day/week names to navigate views
            editable: true,
            selectable: true,
            nowIndicator: true,
            dayMaxEvents: true, // allow "more" link when too many events
            events: data,
            eventTextColor : 'black',
            eventClick: function(calEvent, jsEvent, view) {
                calEvent.jsEvent.preventDefault(); // don't let the browser navigate

                $.ajax({
                    url: "taskCalenPopup",
                    type: "post",
                    data: {tsno: calEvent.event.id}
                }).success(function(result){
                        $("#calendarItem").html(result);
                    }
                );
                $("#calendarItem").modal("show");
            }
        });
        calendar.render();
    });
</script>    
</head>

<body>

    <div id="wrapper">

        <jsp:include page="../common/navigation.jsp" />
        
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><i class="fa fa-tasks fa-fw"></i> <s:message code="project.title"/></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            	<div class="col-lg-12">
            		<c:out value="${projectInfo.prtitle}"/> ( <c:out value="${projectInfo.prstartdate}"/> ~ <c:out value="${projectInfo.prenddate}"/>)
            		<c:if test="${projectInfo.userno==sessionScope.userno}">
            			<a href="projectForm?prno=<c:out value="${projectInfo.prno}"/>"><i class="fa fa-edit fa-fw" title="<s:message code="common.btnUpdate"/>"></i></a>
            		</c:if>
				</div>
            </div>        
            <p>&nbsp;</p>         
            <!-- /.row -->
            <div class="row">
                <ul class="nav nav-pills">
                     <li><a href='task?prno=<c:out value="${prno}" />'><i class="fa fa-tasks fa-fw"></i><s:message code="project.taskMgr"/></a></li>
                     <li class="active"><a href="#profile" data-toggle="tab"><i class="fa fa-calendar  fa-fw"></i><s:message code="project.calendar"/></a></li>
                     <li><a href="taskWorker?prno=<c:out value="${prno}" />"><i class="fa fa-user fa-fw"></i><s:message code="project.taskWorker"/></a></li>
                     <li><a href="taskMine?prno=<c:out value="${prno}" />"><s:message code="project.taskMine"/></a></li>
                 </ul>
            </div>    
            <p>&nbsp;</p>            
            <!-- /.row -->
            <div class="row">
                <div id='calendar-container'>
                    <div id='calendar'></div>
                </div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
    
    <div id="calendarItem" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    </div>
    
</body>

</html>
