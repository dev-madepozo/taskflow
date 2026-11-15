import { useColorMode } from "@vueuse/core";

export function useTheme() {
    const mode = useColorMode({
        attribute: 'class',
        storageKey: 'taskflow-theme',
        initialValue: 'auto',
    })

    function toggleTheme() {
        mode.value = mode.value === 'dark' ? 'light' : 'dark'
    }

    return {
        mode,
        toggleTheme
    }
}
