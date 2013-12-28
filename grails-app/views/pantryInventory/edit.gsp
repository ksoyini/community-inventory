<%@ page import="scott.kellie.PantryInventory" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pantryInventory.label', default: 'PantryInventory')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-pantryInventory" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-pantryInventory" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${pantryInventoryInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${pantryInventoryInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${pantryInventoryInstance?.id}" />
				<g:hiddenField name="version" value="${pantryInventoryInstance?.version}" />
				<fieldset class="form">
                    <div class="fieldcontain">
                        <span id="pantryItem-label" class="property-label"><g:message code="pantryInventory.pantryItem.label" default="Pantry Item" /></span>

                        <span class="property-value" aria-labelledby="pantryItem-label">${pantryInventoryInstance?.pantryItem?.name?.encodeAsHTML()}</span>

                    </div>
                    <div class="fieldcontain ${hasErrors(bean: pantryInventoryInstance, field: 'quantity', 'error')} required">
                        <label for="quantity">
                            <g:message code="pantryInventory.quantity.label" default="Quantity" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:field name="quantity" type="number" min="1" value="${pantryInventoryInstance.quantity}" required=""/>
                    </div>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
