
<%@ page import="scott.kellie.PantryInventory" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pantryInventory.label', default: 'PantryInventory')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-pantryInventory" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-pantryInventory" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="pantryInventory.pantryItem.label" default="Pantry Item" /></th>
					
						<g:sortableColumn property="quantity" title="${message(code: 'pantryInventory.quantity.label', default: 'Quantity')}" />
					
						<th><g:message code="pantryInventory.family.label" default="Family" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pantryInventoryInstanceList}" status="i" var="pantryInventoryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${pantryInventoryInstance.id}">${fieldValue(bean: pantryInventoryInstance, field: "pantryItem")}</g:link></td>
					
						<td>${fieldValue(bean: pantryInventoryInstance, field: "quantity")}</td>
					
						<td>${fieldValue(bean: pantryInventoryInstance, field: "family")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${pantryInventoryInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
