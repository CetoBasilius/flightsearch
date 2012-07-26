Tapestry.Initializer.ckwindow = function(Options)
{
    var win = new Window(Options.windowoptions);
    if (Options.hasbody){
      win.setContent(Options.contentid);
    }
    if (Options.show){
      if (Options.center){
        win.showCenter(Options.modal);
      }else{
        win.show(Options.modal);
      }
    }
    $T(Options.clientid).ck_window = win;
};