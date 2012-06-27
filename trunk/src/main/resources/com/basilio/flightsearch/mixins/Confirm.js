/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/25/12
 * Time: 8:59 PM
 * A small popup window to confirm if the user wants to continue or not, with a customizable message.
 */
var Confirm = Class.create();
Confirm.prototype = {
        initialize: function(element, message) {
                this.message = message;
                Event.observe($(element), 'click', this.doConfirm.bindAsEventListener(this));
        },

        doConfirm: function(e) {
                if(! confirm(this.message))
                        e.stop();
        }
}