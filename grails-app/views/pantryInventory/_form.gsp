<%@ page import="scott.kellie.PantryInventory" %>



<div class="fieldcontain ${hasErrors(bean: pantryInventoryInstance, field: 'pantryItem', 'error')} required">
	<label for="pantryItem">
		<g:message code="pantryInventory.pantryItem.label" default="Pantry Item" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pantryItem" name="pantryItem.id" from="${scott.kellie.PantryItem.list()}" optionKey="id" optionValue="name" required="" value="${pantryInventoryInstance?.pantryItem?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pantryInventoryInstance, field: 'quantity', 'error')} required">
	<label for="quantity">
		<g:message code="pantryInventory.quantity.label" default="Quantity" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="quantity" type="number" min="1" value="${pantryInventoryInstance.quantity}" required=""/>
</div>

%{--As of now: Can only make changes to items in your own family's pantry--}%
%{--<div class="fieldcontain ${hasErrors(bean: pantryInventoryInstance, field: 'family', 'error')} required">--}%
	%{--<label for="family">--}%
		%{--<g:message code="pantryInventory.family.label" default="Family" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:select id="family" name="family.id" from="${scott.kellie.Family.list()}" optionKey="id" required="" value="${pantryInventoryInstance?.family?.id}" class="many-to-one"/>--}%
%{--</div>--}%

