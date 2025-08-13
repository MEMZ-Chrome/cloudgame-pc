(function() {
    // 检查是否启用触屏模式
    if (!window.CG_BRIDGE || !window.CG_BRIDGE.isTouchModeEnabled()) {
        return;
    }
    
    // 创建按钮容器
    const buttonContainer = document.createElement('div');
    buttonContainer.id = 'touch-buttons-container';
    buttonContainer.style.cssText = `
        position: fixed;
        top: 10px;
        left: 10px;
        z-index: 9999;
        display: flex;
        gap: 5px;
        pointer-events: none;
    `;
    
    // 按钮样式
    const buttonStyle = `
        width: 40px;
        height: 40px;
        background: rgba(0, 0, 0, 0.5);
        color: white;
        border: none;
        border-radius: 5px;
        font-size: 14px;
        cursor: pointer;
        pointer-events: auto;
        user-select: none;
    `;
    
    // 创建ESC按钮
    const escButton = document.createElement('button');
    escButton.textContent = 'ESC';
    escButton.style.cssText = buttonStyle;
    escButton.onclick = function() {
        document.dispatchEvent(new KeyboardEvent('keydown', {key: 'Escape', keyCode: 27, which: 27}));
    };
    
    // 创建O按钮
    const oButton = document.createElement('button');
    oButton.textContent = 'O';
    oButton.style.cssText = buttonStyle;
    oButton.onclick = function() {
        document.dispatchEvent(new KeyboardEvent('keydown', {key: 'o', keyCode: 79, which: 79}));
    };
    
    // 创建Enter按钮
    const enterButton = document.createElement('button');
    enterButton.textContent = '↵';
    enterButton.style.cssText = buttonStyle;
    enterButton.onclick = function() {
        document.dispatchEvent(new KeyboardEvent('keydown', {key: 'Enter', keyCode: 13, which: 13}));
    };
    
    // 添加按钮到容器
    buttonContainer.appendChild(escButton);
    buttonContainer.appendChild(oButton);
    buttonContainer.appendChild(enterButton);
    
    // 添加到页面
    document.body.appendChild(buttonContainer);
})();

