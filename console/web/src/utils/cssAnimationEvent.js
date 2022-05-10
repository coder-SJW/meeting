let transitionEndEvent
let animationEndEvent

export function getTransitionEndEvent() {
    if (transitionEndEvent !== undefined) return transitionEndEvent

    if (window.ontransitionend !== undefined) {
        transitionEndEvent = 'transitionend'
    } else if (window.onwebkittransitionend !== undefined) {
        transitionEndEvent = 'webkittransitionend'
    } else {
        transitionEndEvent = ''
    }
    return transitionEndEvent
}

export function getAnimationEndEvent() {
    if (animationEndEvent !== undefined) return animationEndEvent

    if (window.onanimationend !== undefined) {
        animationEndEvent = 'animationend'
    } else if (window.onwebkitanimationend !== undefined) {
        animationEndEvent = 'webkitanimationend'
    } else {
        animationEndEvent = ''
    }
    return animationEndEvent
}
