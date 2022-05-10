import Vue from 'vue';
import {isPlainObject} from 'lodash';
// import store from '@/store';
import router from '@/router';

class DialogManager {
    constructor(staticDialogs = {}) {
        this.current = null
        this.dialogs = {}
        this.staticDialogs = staticDialogs
    }

    register(name, componentOptions) {
        if (!this.dialogs[name]) {
            this.dialogs[name] = Vue.extend(componentOptions)
        }
    }

    unregister(name) {
        if (this.dialogs[name]) {
            delete this.dialogs[name]
        }
    }

    open(name, options) {
        this.destroy()

        const {props, on} = options
        const Dialog = this.staticDialogs[name] || this.dialogs[name] || null
        if (!Dialog) {
            process.env.NODE_ENV !== 'production' &&
            /* eslint-disable no-console */
            console.error(
                `Dialog "${name}" is not existing, please register it firstly.`
            )
            return
        }

        // Dialog组件必须有data.visible、事件close
        const instance = new Dialog({
            propsData: props,
            // store,
            router,
            created() {
                if (isPlainObject(on)) {
                    Object.keys(on).forEach(listenerName => {
                        this.$on(listenerName, on[listenerName])
                    })
                }
            }
        })

        instance.$on('close', () => this.destroy());
        instance.$mount();
        document.body.appendChild(instance.$el);
        instance.visible = true;
        this.current = instance;
        return instance
    }

    close() {
        if (this.current) {
            this.current.visible = false
        }
    }

    destroy() {
        let current = this.current;
        if (current) {
            let $el = current.$el;
            document.body.removeChild($el);
            current.$destroy();
            this.current = null
        }
    }
}

DialogManager.install = function (Vue, dialogs) {
    Vue.prototype.$dialog = new DialogManager(dialogs)
};

export default DialogManager
