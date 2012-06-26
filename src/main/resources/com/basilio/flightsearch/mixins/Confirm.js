/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/25/12
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
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