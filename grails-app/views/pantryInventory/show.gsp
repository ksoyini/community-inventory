
<%@ page import="scott.kellie.PantryInventory" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pantryInventory.label', default: 'PantryInventory')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-pantryInventory" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pantryInventory" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pantryInventory">
			
				<g:if test="${pantryInventoryInstance?.pantryItem}">
				<li class="fieldcontain">
					<span id="pantryItem-label" class="property-label"><g:message code="pantryInventory.pantryItem.label" default="Pantry Item" /></span>
					
						<span class="property-value" aria-labelledby="pantryItem-label">${pantryInventoryInstance?.pantryItem?.name?.encodeAsHTML()}</span>
					
				</li>
				</g:if>
			
				<g:if test="${pantryInventoryInstance?.quantity}">
				<li class="fieldcontain">
					<span id="quantity-label" class="property-label"><g:message code="pantryInventory.quantity.label" default="Quantity" /></span>
					
						<span class="property-value" aria-labelledby="quantity-label"><g:fieldValue bean="${pantryInventoryInstance}" field="quantity"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pantryInventoryInstance?.family}">
				<li class="fieldcontain">
					<span id="family-label" class="property-label"><g:message code="pantryInventory.family.label" default="Family" /></span>
					
						<span class="property-value" aria-labelledby="family-label">${pantryInventoryInstance?.family?.familyName?.encodeAsHTML()}</span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${pantryInventoryInstance?.id}" />
					<g:link class="edit" action="edit" id="${pantryInventoryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
