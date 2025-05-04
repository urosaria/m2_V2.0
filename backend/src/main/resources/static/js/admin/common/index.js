  var template = Handlebars.templates.adminTemplate;
  var adminFooter = Handlebars.templates.adminFooter;
  var adminNav = Handlebars.templates.adminNav;

  Handlebars.registerPartial('adminFooter', adminFooter);
  Handlebars.registerPartial('adminNav', adminNav);
  Handlebars.registerPartial("body", $("#contents_template").html());

  Handlebars.registerHelper('isSelected', function (input, color) {
    return input === color ? 'selected' : '';
  });

  var html = template();
  var $body = $('body');
  $body.append(html);
