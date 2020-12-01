import React, {Component} from 'react'

class LoadingIndicator extends Component {
    render() {
        return (
            <div style={{ position: "absolute", left: "0px", top: "45%", width: "100%", textAlign: "center", zIndex: 9999 }}>
                <svg width="80px"  height="80px"  xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" preserveAspectRatio="xMidYMid" className="lds-cube" style={{ background: "none" }}>
                    <g transform="translate(25,25)">
                        <rect ng-attr-x="{{config.dp}}" ng-attr-y="{{config.dp}}" ng-attr-width="{{config.blockSize}}" ng-attr-height="{{config.blockSize}}" ng-attr-fill="{{config.c1}}" x="-15" y="-15" width="30" height="30" fill="#1d3f72" transform="scale(1.151 1.151)">
                            <animateTransform attributeName="transform" type="scale" calcMode="spline" values="1.5;1" keyTimes="0;1" dur="2.2s" keySplines="0 0.5 0.5 1" begin="-0.66s" repeatCount="indefinite">
                            </animateTransform>
                        </rect>
                    </g>
                    <g transform="translate(75,25)">
                        <rect ng-attr-x="{{config.dp}}" ng-attr-y="{{config.dp}}" ng-attr-width="{{config.blockSize}}" ng-attr-height="{{config.blockSize}}" ng-attr-fill="{{config.c2}}" x="-15" y="-15" width="30" height="30" fill="#5699d2" transform="scale(1.20694 1.20694)">
                            <animateTransform attributeName="transform" type="scale" calcMode="spline" values="1.5;1" keyTimes="0;1" dur="2.2s" keySplines="0 0.5 0.5 1" begin="-0.44000000000000006s" repeatCount="indefinite">
                            </animateTransform>
                        </rect>
                    </g>
                    <g transform="translate(25,75)">
                        <rect ng-attr-x="{{config.dp}}" ng-attr-y="{{config.dp}}" ng-attr-width="{{config.blockSize}}" ng-attr-height="{{config.blockSize}}" ng-attr-fill="{{config.c3}}" x="-15" y="-15" width="30" height="30" fill="#d8ebf9" transform="scale(1.40728 1.40728)">
                            <animateTransform attributeName="transform" type="scale" calcMode="spline" values="1.5;1" keyTimes="0;1" dur="2.2s" keySplines="0 0.5 0.5 1" begin="0s" repeatCount="indefinite">
                            </animateTransform>
                        </rect>
                    </g>
                    <g transform="translate(75,75)">
                        <rect ng-attr-x="{{config.dp}}" ng-attr-y="{{config.dp}}" ng-attr-width="{{config.blockSize}}" ng-attr-height="{{config.blockSize}}" ng-attr-fill="{{config.c4}}" x="-15" y="-15" width="30" height="30" fill="#71c2cc" transform="scale(1.28109 1.28109)">
                            <animateTransform attributeName="transform" type="scale" calcMode="spline" values="1.5;1" keyTimes="0;1" dur="2.2s" keySplines="0 0.5 0.5 1" begin="-0.22000000000000003s" repeatCount="indefinite">
                            </animateTransform>
                        </rect>
                    </g>
                </svg>
            </div>
        )
    }
}

export default LoadingIndicator
