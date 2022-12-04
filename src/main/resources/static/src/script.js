const select = document.querySelector('#sectors-select');
const formSavedAlert = document.querySelector('#form-saved-alert');
const formValidationErrorAlert = document.querySelector('#form-validation-error-alert');
const nameInput = document.querySelector('#name-input');
const termsCheckbox = document.querySelector('#terms-checkbox');
const button = document.querySelector('#submit-btn');

const SESSION_KEY_COOKIE = 'sectors__session-key';

const store = {
    user: null,
    sessionKey: null
}

const getSessionKey = () => {
    return Cookies.get(SESSION_KEY_COOKIE) ?? null;
}

const setSessionKey = (sessionKey) => {
    Cookies.set(SESSION_KEY_COOKIE, sessionKey, { expires: 3, path: '/' });
}

const getSectors = () => {
    return new Promise((resolve, reject) => {
        fetch('/sector')
            .then((response) => resolve(response.json()))
            .catch(reject);
    })
}

const getUser = (sessionKey) => {
    if (!sessionKey) return Promise.resolve();
    const params = new URLSearchParams({
        sessionKey
    })
    return new Promise((resolve, reject) => {
        fetch('/user/current?' + params)
            .then((response) => {
                if (response.status === 200) resolve(response.json());
                else if (response.status === 204) resolve(null);
            })
            .catch(reject);
    })
}

const saveForm = () => {
    const selectedOptions = [].slice.call(select.selectedOptions);
    const body = {
        name: nameInput.value,
        involvedSectorsIds: selectedOptions.map(option => option.value),
        agreeToTerms: termsCheckbox.checked
    }

    if (body.involvedSectorsIds.length === 0 || !body.agreeToTerms || !body.name) {
        return Promise.reject();
    }
    const sessionKey = store.sessionKey
    const url = sessionKey ? '/user?' + new URLSearchParams({
        sessionKey
    }) : '/user'

    return new Promise((resolve, reject) => {
        fetch(url, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })
            .then((response) => resolve(response.json()))
            .catch(reject);
    })
}

const appendOption = (option) => {
    select.appendChild(option);
}

const createOptionObject = (sector, level = 1) => {
    const option = document.createElement('option');
    option.value = sector.id;
    const titleWithLevelOffset = '\u00A0'.repeat((level - 1) * 4) + sector.title;
    const titleNode = document.createTextNode(titleWithLevelOffset);
    option.appendChild(titleNode);
    appendOption(option);
    if (sector.children.length > 0) {
        level++;
        sector.children.forEach(sector => {
            createOptionObject(sector, level);
        })
    }
    return option;
}

const fillForm = (user) => {
    nameInput.value = user.name;
    document.querySelectorAll('option').forEach(option => {
        const value = parseInt(option.value);
        if (user.involvedSectorsIds.includes(value)) {
            option.selected = true;
        }
    })
}

const onInit = () => {
    store.sessionKey = getSessionKey();
    getSectors().then((sectors) => {
        sectors.forEach(sector => createOptionObject(sector));
        getUser(store.sessionKey).then((user) => {
            if (user) {
                store.user = user;
                fillForm(store.user);
            }
        });
    });
}

button.addEventListener('click', e => {
    e.preventDefault();
    formValidationErrorAlert.setAttribute('style', 'display: none');
    formSavedAlert.setAttribute('style', 'display: none');
    saveForm().then((response) => {
        store.sessionKey = response.sessionKey;
        setSessionKey(store.sessionKey);
        formSavedAlert.setAttribute('style', '');
    }).catch(() => {
        formValidationErrorAlert.setAttribute('style', '');
    });
});

onInit();