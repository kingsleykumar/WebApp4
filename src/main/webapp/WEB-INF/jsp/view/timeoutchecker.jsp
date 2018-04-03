<%--
  Created by IntelliJ IDEA.
  User: Kingsley Kumar
  Date: 28/11/2017
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript">
    $(document).ready(function () {
        $.active = false;
        $('body').bind('click keypress', function () {
            $.active = true;
        });
        checkActivity(1200000, 60000, 0);
    });

    function checkActivity(timeout, interval, elapsed) {
        if ($.active) {
            elapsed = 0;
            $.active = false;
            $.get('heartbeat');
        }
        if (elapsed < timeout) {
            elapsed += interval;
            setTimeout(function () {
                checkActivity(timeout, interval, elapsed);
            }, interval);
        } else {
            location.reload();
//                window.location = 'http://localhost/test.jsp'; // Redirect to "session expired" page.
        }
    }
</script>