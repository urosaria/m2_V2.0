this["Handlebars"] = this["Handlebars"] || {};
this["Handlebars"]["templates"] = this["Handlebars"]["templates"] || {};

this["Handlebars"]["templates"]["helpers-markdown"] = Handlebars.template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    return "Handlebars.registerHelper('isVowel', function(options) {\n  var regexp = /^[aeiou]/;\n  if (regexp.test(this.name)) {\n    return options.fn(this);\n  } else {\n    return options.inverse(this);\n  }\n});\nHandlebars.registerHelper('email', function (id) {\n  return id + \"@daum.net\";\n});\nHandlebars.registerHelper('select', function(selected, option) {\n  return (selected == option) ? 'selected=\"selected\"' : '';\n});\n\nHandlebars.registerHelper('isSelected', function (input, color) {\n  return input === color ? 'selected' : '';\n});";
},"useData":true});