import Vue from 'vue'
import {formatDate} from '@/utils/TimeUtils'

Vue.filter('formatDate', formatDate);