<%@page contentType="text/html" pageEncoding="UTF-8" %>

<li class="nav-item dropdown">
    <a class="dropdown-toggle nav-link my-1 ml-2" data-toggle="dropdown" aria-expanded="false">${pageContext.response.locale}</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a>
        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">Русский</a>
    </div>
</li>
<script type="text/javascript">
    let currentLocale = "${pageContext.response.locale}";
</script>