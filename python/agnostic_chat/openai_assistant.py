import logging
import os
from typing import Dict, List

import openai
from openai import OpenAI

from .ai_assistant import AiAssistant

logger = logging.getLogger(__name__)


class OpenAiAssistant(AiAssistant):
    """OpenAI assistant implementation."""

    def initialize_model(self) -> None:
        """Initialize the OpenAI client."""
        if self.api_key:
            self.client = OpenAI(api_key=self.api_key)
        else:
            try:
                self.client = OpenAI()
            except Exception:
                self.client = None

    def format_chat(self, chat_history: List[Dict[str, str]]) -> List[Dict[str, str]]:
        """Convert standard chat history to OpenAI message format including system prompt."""
        messages: List[Dict[str, str]] = [
            {"role": "system", "content": self.get_system_instruction()}
        ]
        for item in chat_history:
            role = item.get("role", "user")
            standard_role = "assistant" if role in ("model", "assistant") else role
            messages.append({"role": standard_role, "content": item.get("content", "")})
        return messages

    def send_prompt(self, chat_history: List[Dict[str, str]]) -> str:
        """Generate response from OpenAI for the provided conversation history."""
        if not self.client or not (self.api_key or os.environ.get("OPENAI_API_KEY")):
            return "[Error: OPENAI_API_KEY is missing. Please set it via environment variable or start.py.]"

        formatted_messages = self.format_chat(chat_history)
        try:
            response = self.client.chat.completions.create(
                model=self.model_id,
                messages=formatted_messages,
                temperature=self.temperature,
            )
            return response.choices[0].message.content or ""
        except openai.OpenAIError as e:
            logger.error(f"OpenAI API Error: {e}")
            return f"[OpenAI API Error: {str(e)}]"
        except Exception as e:
            logger.error(f"Unexpected error communicating with OpenAI: {e}")
            return f"[Error: {str(e)}]"
