<%@ taglib uri="/webwork" prefix="ww" %>
<html>
  <ww:i18n name="localization.MavenUser">
    <head>
      <title><ww:text name="user.delete.page.title"/></title>
    </head>
    <body>
      <div id="axial" class="h3">
        <h3><ww:text name="user.delete.section.title"/></h3>
        <div class="warningmessage">
          <p>
            <strong>
              <ww:text name="user.delete.confirmation.message">
                <ww:param><ww:property value="username"/></ww:param>
              </ww:text>
            </strong>
          </p>
        </div>
        <div class="functnbar3">
          <ww:form action="delete.action" method="post">
            <ww:hidden name="id"/>
            <ww:submit value="%{getText('delete')}"/> <!-- todo: change to submit/cancel button -->
            <input type="button" value="<ww:text name="cancel"/>" onclick="history.go(-1)">
          </ww:form>
        </div>
      </div>
    </body>
  </ww:i18n>
</html>