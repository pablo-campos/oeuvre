import os
from typing import Dict, List

from .ai_assistant import AiAssistant
from .gemini_assistant import GeminiAssistant
from .openai_assistant import OpenAiAssistant

# ****************************************** Parameters *********************************************

# Note: Set your API keys via environment variables or provide them below.
# E.g., in bash/zsh:
#   export OPENAI_API_KEY="sk-..."
#   export GEMINI_API_KEY="AIzaSy..."
OPENAI_API_KEY = os.environ.get("OPENAI_API_KEY", "")
GEMINI_API_KEY = os.environ.get("GEMINI_API_KEY", "")

# Default model identifiers
MODEL_OPENAI = os.environ.get("OPENAI_MODEL", "gpt-4o")
MODEL_GEMINI = os.environ.get("GEMINI_MODEL", "gemini-2.5-flash")

USER_NAME = os.environ.get("USER_NAME", "Pablo")


# ****************************************** Interactive Chat ***************************************

def start_interactive_chat() -> None:
    # Check for missing API keys and display guidance
    missing_keys = []
    if not OPENAI_API_KEY:
        missing_keys.append(("OpenAI", "OPENAI_API_KEY"))
    if not GEMINI_API_KEY:
        missing_keys.append(("Gemini", "GEMINI_API_KEY"))

    if missing_keys:
        print("=" * 65)
        print(" API KEY NOTICE:")
        print("To enable chat functionality, set the required API key(s):")
        for provider, var_name in missing_keys:
            print(f"  • {provider}: export {var_name}=\"your-key-here\"")
        print("=" * 65 + "\n")

    print(f"Initializing OpenAI model ({MODEL_OPENAI})...")
    openai_assistant = OpenAiAssistant(
        api_key=OPENAI_API_KEY,
        model_id=MODEL_OPENAI,
        user_name=USER_NAME,
    )
    openai_assistant.setup_model()

    print(f"Initializing Gemini model ({MODEL_GEMINI})...\n")
    gemini_assistant = GeminiAssistant(
        api_key=GEMINI_API_KEY,
        model_id=MODEL_GEMINI,
        user_name=USER_NAME,
    )
    gemini_assistant.setup_model()

    assistants: Dict[str, AiAssistant] = {
        "openai": openai_assistant,
        "gemini": gemini_assistant,
    }

    active_assistant: AiAssistant = openai_assistant
    chat_history: List[Dict[str, str]] = []

    print("Available commands:")
    print("  gemini : Switch to Gemini model")
    print("  openai : Switch to OpenAI model")
    print("  chat   : Print conversation history")
    print("  clear  : Clear conversation history")
    print("  quit   : Exit chat")
    print(f"\nCurrent Model: {active_assistant.model_id}\n")

    while True:
        try:
            user_message = input("You: ").strip()
        except (KeyboardInterrupt, EOFError):
            break

        if not user_message:
            continue

        cmd = user_message.lower()
        if cmd == "quit":
            break
        elif cmd in assistants:
            active_assistant = assistants[cmd]
            print(f"Switched model to: {active_assistant.model_id}\n")
        elif cmd == "chat":
            if not chat_history:
                print("(Chat history is currently empty)\n")
            else:
                for msg in chat_history:
                    role_label = "You" if msg["role"] == "user" else "Assistant"
                    print(f"[{role_label}]: {msg['content']}")
                print()
        elif cmd == "clear":
            chat_history.clear()
            print("Chat history cleared.\n")
        else:
            chat_history.append({"role": "user", "content": user_message})
            ai_response = active_assistant.send_prompt(chat_history)
            print(f"\n{active_assistant.assistant_name} ({active_assistant.model_id}):\n{ai_response}\n")
            chat_history.append({"role": "assistant", "content": ai_response})

    print("\nConversation ended.\n")


if __name__ == "__main__":
    start_interactive_chat()
