Handlebars.registerHelper('isVowel', function(options) {
  var regexp = /^[aeiou]/;
  if (regexp.test(this.name)) {
    return options.fn(this);
  } else {
    return options.inverse(this);
  }
});
Handlebars.registerHelper('email', function (id) {
  return id + "@daum.net";
});
Handlebars.registerHelper('select', function(selected, option) {
  return (selected == option) ? 'selected="selected"' : '';
});

Handlebars.registerHelper('isSelected', function (input, color) {
  return input === color ? 'selected' : '';
});