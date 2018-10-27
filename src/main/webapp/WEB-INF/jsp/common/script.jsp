<%-- 
    Document   : script
    Created on : 1 Oct, 2016, 11:11:14 AM
    Author     : shrutika
--%>

<!-- START PRELOADS -->
<audio id="audio-alert" src="../audio/alert.mp3" preload="auto"></audio>
<audio id="audio-fail" src="../audio/fail.mp3" preload="auto"></audio>
<!-- END PRELOADS -->                  

<!-- START SCRIPTS -->
<!-- START PLUGINS -->
<script type="text/javascript" src="../js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/plugins/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/plugins/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-datepicker.js"></script>
<!-- END PLUGINS -->

<script type="text/javascript">
    $(document).ready(function() {
        $(".datepicker").datepicker({format: 'yyyy-mm-dd', autoclose: true});
    });
</script>

<!-- END SCRIPTS -->         