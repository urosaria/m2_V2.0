this["Handlebars"] = this["Handlebars"] || {};
this["Handlebars"]["templates"] = this["Handlebars"]["templates"] || {};

this["Handlebars"]["templates"]["adminTemplate"] = Handlebars.template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = container.invokePartial(partials.adminNav,depth0,{"name":"adminNav","data":data,"helpers":helpers,"partials":partials,"decorators":container.decorators})) != null ? stack1 : "")
    + "\n<div class=\"content-wrapper\">\n    <div class=\"container-fluid\">\n        <div id=\"real_contents\">\n"
    + ((stack1 = container.invokePartial(partials.body,depth0,{"name":"body","data":data,"indent":"            ","helpers":helpers,"partials":partials,"decorators":container.decorators})) != null ? stack1 : "")
    + "        </div>\n    </div>\n</div>\n\n"
    + ((stack1 = container.invokePartial(partials.adminFooter,depth0,{"name":"adminFooter","data":data,"helpers":helpers,"partials":partials,"decorators":container.decorators})) != null ? stack1 : "");
},"usePartial":true,"useData":true});