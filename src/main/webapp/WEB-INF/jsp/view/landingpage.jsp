<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 31/12/2017
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="jumbotron jumbotron-fluid">
    <div class="container-fluid jumbotron-content">

        <div class="container-fluid" style="text-align: center; padding-bottom: 20px">
            <h2>Manage your expenses and Save money</h2>
        </div>


        <div class="row">
            <div class="col-md-6 jumbotron-content-left">

                <%--<p class="jumbotron-p">Get your personal, household finances under control</p>--%>
                <%--<p class="jumbotron-p">Get a clear picture of where your money goes, where it comes from</p>--%>
                <%--<p class="jumbotron-p">Take informed decisions on your expenses, savings and plan for better financial--%>
                <%--future</p>--%>

                <ul style="font-size: 17px">
                    <li>Get your personal, household finances under control</li>
                    <li>Get a clear picture of where your money goes, where it comes from</li>
                    <li>Take informed decisions on your expenses, savings and plan for better financial
                        future
                    </li>
                </ul>


                <div class="continer-fluid jumbotron-signup" style="alignment: center;">
                    <a href="<c:url value="/signup" />" class="btn btn-success navbar-btn" role="button">
                        <i class="glyphicon glyphicon-lock"></i> Sign Up For Free
                    </a>
                </div>

            </div>

            <div class="col-md-6">

                <div class="container-fluid budget" id="budget" align="center">
                    <img class="img-responsive" class="budgetImg" src="/images/budget-view.png"/>
                </div>

            </div>
        </div>

    </div>

</div>

<!-- Icons Grid -->
<section class="features-icons bg-light text-center">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <%--<div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">--%>
                <%--<div class="features-icons-icon d-flex">--%>
                <%--<i class="icon-screen-desktop m-auto text-primary"></i>--%>
                <%--</div>--%>
                <h3>Plan</h3>
                <p class="lead mb-0" style="font-size: 15px; font-weight: 400; color:#575757;">Set up a budget that
                    makes sense for you.
                    Plan ahead and check if you are living within
                    your means and saving enough money for your long term goals.
                </p>
                <%--</div>--%>
            </div>
            <div class="col-md-4">
                <%--<div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">--%>
                <%--<div class="features-icons-icon d-flex">--%>
                <%--<i class="icon-layers m-auto text-primary"></i>--%>
                <%--</div>--%>
                <h3>Monitor</h3>
                <p class="lead mb-0" style="font-size: 15px; font-weight: 400; color:#575757">Monitor what you are
                    spending and where
                    money actually is going - the first step to cutting back</p>
                <%--</div>--%>
            </div>
            <div class="col-md-4">
                <%--<div class="features-icons-item mx-auto mb-0 mb-lg-3">--%>
                <%--<div class="features-icons-icon d-flex">--%>
                <%--<i class="icon-check m-auto text-primary"></i>--%>
                <%--</div>--%>
                <h3>Assess</h3>
                <p class="lead mb-0" style="font-size: 15px; font-weight: 400; color:#575757">Evaluate the instant
                    changes happening in
                    your budget and assess how it impacts your current and future financial commitments</p>
            </div>
            <%--</div>--%>
        </div>
    </div>
</section>

<%--<section class="showcase">--%>
<%--<div class="container-fluid p-0">--%>
<%--<div class="row no-gutters">--%>

<%--<div class="col-lg-6 order-lg-2 text-white showcase-img" style="background-image: url('images/image1.jpg');"></div>--%>
<%--<div class="col-lg-6 order-lg-1 my-auto showcase-text">--%>
<%--<h2>Fully Responsive Design</h2>--%>
<%--<p class="lead mb-0">When you use a theme created by Start Bootstrap, you know that the theme will look great on any device, whether it's a phone, tablet, or desktop the page will behave responsively!</p>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</section>--%>

<!-- Image Showcases -->
<section class="showcase">
    <div class="container-fluid p-0">
        <div class="row no-gutters">

            <div class="col-md-6 order-md-2 my-auto showcase-img-div monthly-breakdown">
                <div class="container-fluid" align="center">
                    <img class="img-responsive" src="/images/monthly-breakdown.png"/>
                </div>
            </div>

            <%--<div class="col-lg-6">--%>
            <%--<h4>How does it work?</h4>--%>

            <%--</div>--%>
            <%--style="background-image: url('images/summary1.jpg');"></div>--%>
            <div class="col-md-6 order-md-1 my-auto showcase-text">
                <%--<div class="col-lg-6 order-lg-1 my-auto showcase-text" style="background-color: rgba(91, 192, 222, 0.7)">--%>

                <h4>How does it work?</h4>

                <ul style="font-size: 16px">
                    <li>Create transactions and group them under category, sub-category.</li>
                    <li>Plan your spending ahead by setting up a budget.</li>
                    <li>Summary view on a budget gives you clear view on your progress so far and helps you to
                        be on top of your budget goal.
                    </li>
                    <li>Breakdown your transactions by Monthly, Weekly, Yearly within selected time period. Compare
                        it with previous months, weeks, years to assess progress.
                    </li>
                    <li>Get full clarity on all your incomings and outgoings and find out what direction your finances
                        are heading towards.
                    </li>
                </ul>

                <%--<p class="lead mb-0">When you use a theme created by Start Bootstrap, you know that the theme will look--%>
                <%--great on any device, whether it's a phone, tablet, or desktop the page will behave responsively!</p>--%>
            </div>
        </div>
        <%--<div class="row no-gutters">--%>
        <%--<div class="col-lg-6 text-white showcase-img" style="background-image: url('img/bg-showcase-2.jpg');"></div>--%>
        <%--<div class="col-lg-6 my-auto showcase-text">--%>
        <%--<h2>Updated For Bootstrap 4</h2>--%>
        <%--<p class="lead mb-0">Newly improved, and full of great utility classes, Bootstrap 4 is leading the way in mobile responsive web development! All of the themes on Start Bootstrap are now using Bootstrap 4!</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="row no-gutters">--%>
        <%--<div class="col-lg-6 order-lg-2 text-white showcase-img" style="background-image: url('img/bg-showcase-3.jpg');"></div>--%>
        <%--<div class="col-lg-6 order-lg-1 my-auto showcase-text">--%>
        <%--<h2>Easy to Use &amp; Customize</h2>--%>
        <%--<p class="lead mb-0">Landing Page is just HTML and CSS with a splash of SCSS for users who demand some deeper customization options. Out of the box, just add your content and images, and your new landing page will be ready to go!</p>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
</section>

<section class="showcase" style=" background-color: #fff">
    <div class="container-fluid p-0">
        <div class="row no-gutters">

            <%--style="background-image: url('images/summary1.jpg');"></div>--%>
            <div class="col-md-6 order-md-1 my-auto showcase-text">

                <h4>Visualize your finances</h4>

                <ul style="font-size: 16px">
                    <li>The colorful graphs help you to understand your key financial details more easily.</li>
                    <li>Drill down the chart and drill up for more interactivity.</li>
                </ul>

                <%--<p class="lead mb-0">When you use a theme created by Start Bootstrap, you know that the theme will look--%>
                <%--great on any device, whether it's a phone, tablet, or desktop the page will behave responsively!</p>--%>
            </div>

            <div class="col-md-6 order-md-2 text-white showcase-img-div">
                <div class="container-fluid" align="center">
                    <img src="/images/chart.jpg"/>
                </div>
            </div>
        </div>
    </div>
</section>
<section class="showcase">
    <div class="container-fluid p-0">
        <div class="row no-gutters">

            <%--style="background-image: url('images/summary1.jpg');"></div>--%>
            <div class="col-md-6 order-md-1 my-auto showcase-text">

                <h4>Easy Sign Up and Secure Connection</h4>

                <ul style="font-size: 16px">
                    <li>Your data is stored securely and it is encrypted with a 128-bit encryption level.</li>
                    <li>Communication with our server is encrypted with SHA-2 & 2048-bit encryption.</li>
                </ul>
            </div>

            <div class="col-md-6 order-md-1 my-auto showcase-text">

                <h3>Get started by creating your free account in seconds.</h3>

                <div class="continer-fluid jumbotron-signup" style="alignment: center;">
                    <a href="<c:url value="/signup" />" class="btn btn-success navbar-btn" role="button">
                        <i class="glyphicon glyphicon-lock"></i> Sign Up For Free
                    </a>
                    <%--<a href="<c:url value="/login-simple.jsp" />" class="btn btn-info navbar-btn" role="button">--%>
                    <%--<i class="glyphicon glyphicon-log-in"></i> Sign In--%>
                    <%--</a>--%>
                </div>
            </div>
            <%--<div class="col-lg-6 order-lg-2 text-white showcase-img-div">--%>
            <%--<div class="container-fluid" align="center">--%>
            <%--<img src="images/image2.jpg"/>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</section>

<style>

    .showcase-img-div {

        padding: 3rem;
        /*padding-top: 6rem;*/
        /*background-color: rgba(91, 192, 222, 0.7);*/
        /*border: #ff6661 solid 2px;*/
        height: 100%;
        /*display:table-cell;*/
        /*vertical-align: middle;*/
        /*float: none;*/
        /*display: flex;*/
        /*align-items: center;*/
        /*background-size: cover;*/
        /*align-items: center;*/
    }

    .showcase-img-div img {
        width: 100%;
        /*height: 300px;*/
        /*background-size: cover*/

    }

    .showcase-img-div-2 {
        padding: 20px;
        background-color: #fff;
    }

    .showcase-img-div-2 img {
        width: 100%;
        /*height: 300px;*/
    }

    .showcase {
        background-color: rgba(250, 250, 250, 0.9);
        /*background-color: rgba(91, 192, 222, 0.7);*/
    }

    /*.showcase-text {*/
    /*background-color: rgba(255, 107, 96, 0.7);*/
    /*}*/

    .showcase .showcase-text {

        padding: 3rem;
        /*background-color: rgba(91, 192, 222, 0.7);*/
    }

    /*.showcase-2 .showcase-text {*/
    /*padding: 3rem;*/
    /*background-color: #fff;*/
    /*}*/

    /*.showcase .showcase-img {*/
    /*min-height: 30rem;*/
    /*background-size: cover*/
    /*}*/

    /*.jumbotron-p {*/

    /*!*font-family: 'Poppins', sans-serif;*!*/
    /*font-size: 10px;*/
    /*font-weight: 300;*/
    /*line-height: 1.0em;*/
    /*color: #575757;*/
    /*}*/

    .features-icons {
        padding-top: 7rem;
        padding-bottom: 7rem;
        background-color: #fff;
        /*font-weight: 900;*/
        /*font-family: 'Poppins', sans-serif;*/

        /*background-color: #f6f6f6;*/
        /*border: #7386D5 solid 2px;*/
    }

    /*.features-icons .features-icons-item {*/
    /*max-width: 20rem*/
    /*}*/

    /*.features-icons .features-icons-item .features-icons-icon {*/
    /*height: 7rem*/
    /*}*/

    /*.features-icons .features-icons-item .features-icons-icon i {*/
    /*font-size: 4.5rem*/
    /*}*/

    /*.features-icons .features-icons-item:hover .features-icons-icon i {*/
    /*font-size: 5rem*/
    /*}*/

    .wrapper {

        background-color: #fff;
    }

    .budget img {
        width: 100%;
        height: 100%;
        /*width: 500px;*/
        /*he0%;*/
    }

    .jumbotron {

        /*background-color: #fff;*/
        /*background-color: #5bc0de;*/

        background-color: rgba(91, 192, 222, 0.7);
        /*border: #ff6661 solid 2px;*/
        margin-bottom: 0px;

    }

    .jumbotron-content {
        padding-top: 50px;
        padding-left: 40px;
        padding-right: 60px;
        padding-bottom: 10px;
        /*color: #fff;*/
        /*border: #dade1b solid 2px;*/
    }

    .jumbotron-content-left {
        /*display: flex;*/
        /*min-height: 100%;  !* Fallback for browsers do NOT support vh unit *!*/
        /*min-height: 100vh; !* These two lines are counted as one :-)       *!*/

        /*display: flex;*/
        /*align-items: center;*/

        /*padding-top: 60px;*/
        padding-top: 40px;
        padding-left: 60px;
        /*vertical-align: center;*/
    }

    .jumbotron-signup {
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 50px;
        /*height: 500px;*/
        /*width: 100%;*/
    }

    .card {
        text-align: center;
        border: 1px solid #000;
        /*width: 60%;*/
        padding: 20px;
    }

    @media (min-width: 768px) {
        .showcase .showcase-text {
            padding: 5rem
        }

        .showcase-img-div {
            padding: 5rem;
        }
    }

    @media (max-width: 768px) {

        .jumbotron-content-left {
            padding-left: 10px;
        }

        .jumbotron-content {
            padding-left: 50px;
            padding-right: 50px;
        }
    }

    @media (max-width: 420px) {

        .jumbotron-content {
            padding-top: 50px;
            padding-left: 15px;
            padding-right: 15px;
            padding-bottom: 10px;
            /*color: #fff;*/
            /*border: #dade1b solid 2px;*/
        }

        .showcase-img-div {
            /*padding-top: 6rem;*/
            padding-left: 1rem;
            padding-right: 1rem;
        }
    }

    @media screen and (min-width: 1000px) and (max-width: 1200px) {

        .jumbotron-content {
            padding-top: 50px;
            padding-left: 0px;
            padding-right: 30px;
            padding-bottom: 10px;
            /*color: #fff;*/
            /*border: #dade1b solid 2px;*/
        }

        .showcase-img-div {
            padding-top: 5rem;
            padding-left: 3rem;
            padding-right: 4rem;
        }

        .monthly-breakdown {
            padding-top: 7rem;
            padding-right: 1rem;
        }
    }
</style>