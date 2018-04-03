<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 03/11/2017
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>

<%--<div class="overlay"></div>--%>
<%--@elvariable id="type" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="footer-element.jsp"/>

<!-- jQuery CDN -->
<%--<script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>--%>
<!-- Bootstrap Js CDN -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
<!-- jQuery Nicescroll CDN -->
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.nicescroll/3.6.8-fix/jquery.nicescroll.min.js"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>--%>
<script src="/js/jquery.mCustomScrollbar.concat.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        // $("#sidebar").niceScroll({
        //     cursorcolor: '#53619d',
        //     cursorwidth: 10,
        //     cursorborder: 'none'
        // });

        $('#sidebar').mCustomScrollbar({
            // axis:"yx",
            theme: "minimal"
        });

        var overlaySideBarOn = false;

        window.addEventListener("resize", function (ev) {

            var width = window.innerWidth;

            if (width > 768) {

                if (overlaySideBarOn) {
                    $('#sidebar').removeClass('active');
                    $('.overlay').fadeOut();
                    overlaySideBarOn = !overlaySideBarOn;
                } else {

                    // $('#sidebar').removeClass('active');
                }


                console.log("overlaySideBarOn ===  " + overlaySideBarOn);
                console.log("sidebar class = " + $('#sidebar').attr("class"));
                console.log("content class = " + $('#content').attr("class"));

            } else{

                if (!overlaySideBarOn) {

                    $('#sidebar').removeClass('active');
                    $('#content').removeClass('active');

                }
            }
        });

        $('.overlay').on('click', function () {

            var width = window.innerWidth;

            // console.log("width = " + width);

            if (width <= 768) {
                $('#sidebar').removeClass('active');
                $('.overlay').fadeOut();

                overlaySideBarOn = !overlaySideBarOn;

            } else {

                // $('#sidebar, #content').toggleClass('active');
                // // $('#sidebar').addClass('active');
                // // $('.overlay').fadeIn();
                // $('.collapse.in').toggleClass('in');
                // $('a[aria-expanded=true]').attr('aria-expanded', 'false');
            }

            // console.log("overlaySideBarOn ===  " + overlaySideBarOn);
            // console.log("sidebar class = " + $('#sidebar').attr("class"));
            // console.log("content class = " + $('#content').attr("class"));
        });

        $('#sidebarCollapse').on('click', function () {

            var width = window.innerWidth;

            // console.log("width = " + width);

            if (width <= 768) {

                if (overlaySideBarOn) {
                    $('#sidebar').removeClass('active');
                    $('.overlay').fadeOut();

                } else {

                    $('#sidebar').addClass('active');
                    $('.overlay').fadeIn();
                }

                $('.collapse.in').toggleClass('in');
                $('a[aria-expanded=true]').attr('aria-expanded', 'false');

                overlaySideBarOn = !overlaySideBarOn;

            } else {

                $('#sidebar, #content').toggleClass('active');

                // $('#sidebar').addClass('active');
                $('.overlay').fadeOut();
                $('.collapse.in').toggleClass('in');
                $('a[aria-expanded=true]').attr('aria-expanded', 'false');
            }

            // console.log("overlaySideBarOn ===  " + overlaySideBarOn);
            // console.log("sidebar class = " + $('#sidebar').attr("class"));
            // console.log("content class = " + $('#content').attr("class"));
        });
    });
</script>

</body>
</html>
